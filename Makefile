
all	: clean compile tetdm1024_jar tetdm512_jar class_jar

compile	: 
	javac ./source/*.java ./source/*/*.java ./module/*/*/*.java

clean:
	-rm -r ./source/*.class ./source/*/*.class ./module/*/*/*.class

tetdm1024_jar:
	jar -cvfm ./TETDM1024.jar ./MANIFEST1024.MF source/tetdm1024.class

tetdm512_jar:
	jar -cvfm ./TETDM512.jar ./MANIFEST512.MF source/tetdm512.class

class_jar:
	jar -cvfm ./TETDM.jar ./MANIFEST.MF source/*.class source/*/*.class ./module/*/*/*.class
#	jar -cvfm ./TETDM.jar ./MANIFEST.MF source/*.class source/*/*.class ./module/*/*/*.class source/*.png

#	jar -cvfm ./TETDM_test.jar ./MANIFEST.MF source/*.class source/*/*.class 
	-rm -r ./source/*.class ./source/*/*.class
#	-rm .DS_Store
#	-rm */.DS_Store
#	-rm */*/.DS_Store

