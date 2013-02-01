DiskBiggest
===========

Tools that scans disk and find biggest N directories. Useful to clean disks.

I know that the same could be achieved with unix command line: `du -h <DIR> | sort -h -r | head -n 30`
But this tool is multi-platform and use less memory.

When compiled or downloaded JAR could be used as following:
`java -jar diskbiggest-1.0.jar . 30`