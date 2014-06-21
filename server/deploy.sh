mvn clean package
mv target/bringste.war ../../bringste-deploy/webapps/ROOT.war
cd ../../bringste-deploy
git commit -am "update bringste"
git push origin
cd -
