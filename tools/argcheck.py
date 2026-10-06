import re,glob,sys
decl={}
for f in glob.glob('blitz3d-ng/src/modules/bb/*/commands.decls')+glob.glob('run_ue/*.decls')+glob.glob('port_ue/*.decls'):
    for l in open(f,errors='ignore'):
        m=re.match(r'\s*(\w+)[%#$.\w]*\s*\((.*?)\)\s*:',l)
        if m:
            ps=[p for p in m.group(2).split(',') if p.strip()]
            opt=sum(1 for p in ps if '=' in p)
            decl[m.group(1).lower()]=(len(ps)-opt,len(ps),f.split('/')[-2])
# user functions
user={}
files=[f for f in ['run_ue/Game.bb']+glob.glob('run_ue/Source Code/*.bb')+glob.glob('run_ue/*.bb') if not re.search(r'Source Code.(Deferred|Shaders)_Core|IniController.bb',f)]
for f in set(files):
    for l in open(f,errors='ignore'):
        m=re.match(r'\s*Function\s+(\w+)[%#$.\w]*\s*\((.*)\)',l)
        if m:
            ps=[p for p in re.split(r',(?![^"]*")',m.group(2)) if p.strip()]
            opt=sum(1 for p in ps if '=' in p)
            user[m.group(1).lower()]=(len(ps)-opt,len(ps))
def split_args(s):
    d=0;cur='';out=[];q=False
    for ch in s:
        if ch=='"': q=not q
        if not q:
            if ch in '([': d+=1
            if ch in ')]': d-=1
            if ch==',' and d==0: out.append(cur);cur='';continue
        cur+=ch
    if cur.strip(): out.append(cur)
    return out
bad={}
for f in set(files):
    for i,l in enumerate(open(f,errors='ignore')):
        l=re.sub(r';.*$','',l)
        l2=re.sub(r'"[^"]*"','""',l)
        for m in re.finditer(r'\b(\w+)\s*[%#$]?\s*\(',l2):
            n=m.group(1).lower()
            if n in user: continue
            elif n in decl: lo,hi,_=decl[n]
            else: continue
            # extract arg text
            j=m.end();d=1;k=j
            while k<len(l2) and d:
                if l2[k]=='(':d+=1
                if l2[k]==')':d-=1
                k+=1
            args=split_args(l2[j:k-1])
            c=len(args)
            if re.match(r'\s*Function\b',l2,re.I): continue
            if c<lo or c>hi: bad.setdefault((n,c,lo,hi),[]).append(f.split('/')[-1]+':'+str(i+1))
for k,v in sorted(bad.items()): print(k,len(v),v[0])
