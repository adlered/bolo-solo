workdir=$(cd $(dirname $0); pwd)

cd ${workdir}
cd ..
npm run build
rm -rf emiya.zip
zip -r emiya.zip emiya -x "./emiya/.git/*" -x emiya/.gitignore -x emiya/.DS_Store