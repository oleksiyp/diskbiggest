DiskBiggest
===========

Tools that scans disk and find biggest directories.
Useful to clean disk.
I know that the same could be achieved with unix command line: `du -h <DIR> | sort -h -r | head -n 30`
But this tool is multiplatform and use less memory.
When compiled or downloded JAR could be used as following:
`java -jar diskbiggest-1.0.jar .`