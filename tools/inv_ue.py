import re,glob,collections
files=[f for f in ['run_ue/Game.bb']+glob.glob('run_ue/Source Code/*.bb')+glob.glob('run_ue/*.bb') if not re.search(r'Source Code.(Deferred|Shaders)_Core|IniController.bb|RMesh_Model',f)]
known=set()
for f in glob.glob('blitz3d-ng/src/modules/bb/*/commands.decls')+glob.glob('run_ue/*.decls'):
    for l in open(f,errors='ignore'):
        m=re.match(r'\s*(\w+)[%#$.\w]*\s*\(',l)
        if m: known.add(m.group(1).lower())
kw=set(l.split()[0].lower().rstrip('%#$') for l in open('run_ue/kw.txt',errors='ignore') if l.strip()) if glob.glob('run_ue/kw.txt') else set()
txt={f:re.sub(r'"[^"\n]*"','""',open(f,errors='ignore').read()) for f in files}
for t in txt.values():
    for m in re.finditer(r'(?im)^\s*Function\s+(\w+)',t): known.add(m.group(1).lower())
    for m in re.finditer(r'(?im)^\s*(?:Global|Local|Field|Dim|Const)\s+(.*)$',t):
        for v in re.finditer(r'(\w+)[%#$]?(\.\w+)?\s*(?:\[|=|,|$)',m.group(1)): known.add(v.group(1).lower())
    for m in re.finditer(r'(?im)^\s*Type\s+(\w+)',t): known.add(m.group(1).lower())
cnt=collections.Counter();where={}
for f,t in txt.items():
    t=re.sub(r';.*','',t)
    for m in re.finditer(r'(?<![\.\w])([A-Za-z_]\w*)\s*[%#$]?\s*\(',t):
        n=m.group(1).lower()
        if n in known: continue
        cnt[n]+=1;where.setdefault(n,f.split('/')[-1])
for n,c in cnt.most_common(): print(n,c,where[n])
