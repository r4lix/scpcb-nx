#!/usr/bin/env python3
"""Pack a Switch homebrew ELF plus a data directory into an installable NSP.

    python3 tools/nsp/mknsp.py --elf build/scpcb.elf --romfs <dir> --icon icon.jpg \
        --keys "<path>/prod.keys" --out build/nsp

The ELF becomes the title's exefs (NSO + NPDM), <dir> becomes the title's romfs, and the NACP
declares device save data so the game can keep its saves like a retail title does. hacBrewPack
(tools/nsp/bin, GPLv2, dragonflylee fork of The-4n/hacBrewPack) builds the NCAs.

Run it from MSYS (devkitPro's bash): the tools it calls are Windows executables found through
$DEVKITPRO/tools/bin.  The NSP is signed with the fake "homebrew" scheme, so the console needs
Atmosphere signature patches (sigpatches) to install it.
"""
import argparse, json, os, shutil, struct, subprocess, sys

HERE = os.path.dirname(os.path.abspath(__file__))
DEVKITPRO = os.environ.get("DEVKITPRO", "/opt/devkitpro")
TOOLS = os.path.join(DEVKITPRO, "tools", "bin")

MB = 1024 * 1024
# NacpStruct offsets (libnx switch/nacp.h)
OFF_STARTUP_USER_ACCOUNT = 0x3025
OFF_SAVE_DATA_OWNER_ID = 0x3078
OFF_DEVICE_SAVE_SIZE = 0x3090
OFF_DEVICE_SAVE_JOURNAL = 0x3098


def run(cmd, **kw):
    print("+", " ".join(str(c) for c in cmd))
    subprocess.run(cmd, check=True, **kw)


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--elf", required=True, help="linked homebrew ELF (the one the NRO is made from)")
    ap.add_argument("--romfs", required=True, help="directory packed as the title's romfs")
    ap.add_argument("--icon", required=True, help="256x256 JPEG, no EXIF")
    ap.add_argument("--keys", required=True, help="prod.keys")
    ap.add_argument("--out", required=True, help="output directory")
    ap.add_argument("--titleid", default="0x01005CB000010000",
                    help="16 hex digits, 0x0100.. - 0x01ff.., low 12 bits zero (default: %(default)s)")
    ap.add_argument("--name", default="SCP: Containment Breach")
    ap.add_argument("--author", default="r4lix")
    ap.add_argument("--version", default="0.1.0", help="display version shown on the home menu")
    ap.add_argument("--save-mb", type=int, default=32, help="device save data size")
    ap.add_argument("--journal-mb", type=int, default=32, help="device save data journal size")
    a = ap.parse_args()

    tid = int(a.titleid, 16)
    if not (0x0100000000000000 <= tid <= 0x01FFFFFFFFFFFFFF) or tid & 0xFFF:
        sys.exit("title id must be in 0x0100.. - 0x01ff.. with the low 12 bits zero")
    tid_s = "0x%016x" % tid  # hacBrewPack wants lower-case

    out = os.path.abspath(a.out)
    work = os.path.join(out, "work")
    shutil.rmtree(work, ignore_errors=True)
    for d in ("exefs", "control"):
        os.makedirs(os.path.join(work, d))

    # exefs: main (NSO) + main.npdm
    run([os.path.join(TOOLS, "elf2nso.exe"), a.elf, os.path.join(work, "exefs", "main")])
    npdm = json.load(open(os.path.join(HERE, "npdm.template.json")))
    npdm.update(name=a.name[:0x10] or "Application", title_id=tid_s)
    npdm_json = os.path.join(work, "npdm.json")
    json.dump(npdm, open(npdm_json, "w"), indent=2)
    run([os.path.join(TOOLS, "npdmtool.exe"), npdm_json, os.path.join(work, "exefs", "main.npdm")])

    # control: NACP (+ save data fields) and icon
    nacp = os.path.join(work, "control", "control.nacp")
    run([os.path.join(TOOLS, "nacptool.exe"), "--create", a.name, a.author, a.version, nacp])
    data = bytearray(open(nacp, "rb").read())
    data[OFF_STARTUP_USER_ACCOUNT] = 0  # no profile picker: device save data is account-less
    struct.pack_into("<Q", data, OFF_SAVE_DATA_OWNER_ID, tid)
    struct.pack_into("<Q", data, OFF_DEVICE_SAVE_SIZE, a.save_mb * MB)
    struct.pack_into("<Q", data, OFF_DEVICE_SAVE_JOURNAL, a.journal_mb * MB)
    open(nacp, "wb").write(data)
    shutil.copy(a.icon, os.path.join(work, "control", "icon_AmericanEnglish.dat"))

    nsp_dir = os.path.join(out, "nsp")
    shutil.rmtree(nsp_dir, ignore_errors=True)
    run([os.path.join(HERE, "bin", "hacbrewpack.exe"),
         "-k", a.keys,
         "--titleid", tid_s,
         "--exefsdir", os.path.join(work, "exefs"),
         "--controldir", os.path.join(work, "control"),
         "--romfsdir", os.path.abspath(a.romfs),
         "--nspdir", nsp_dir,
         "--ncadir", os.path.join(work, "nca"),
         "--tempdir", os.path.join(work, "temp"),
         "--backupdir", os.path.join(work, "backup"),
         "--nologo"])

    for f in os.listdir(nsp_dir):
        if f.endswith(".nsp"):
            dst = os.path.join(nsp_dir, "%s [%s][v0].nsp" % (a.name.replace(":", ""), tid_s[2:].upper()))
            os.replace(os.path.join(nsp_dir, f), dst)
            print("\n%s  %.1f MB" % (dst, os.path.getsize(dst) / MB))
            return
    sys.exit("hacBrewPack produced no .nsp")


if __name__ == "__main__":
    main()
