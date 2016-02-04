# Test format (column are separated by two tabs):
#	<size>		<inputUnit>		<resultUnit>		<formattedSize>
#
# For the units use the following values (according to static fields in FileInfo class):
#	- 0, for auto detected unit
#	- 1 for B
#	- 1000 for KB
#	- 1000000 for MB
#	- 1000000000 for GB

# basic tests
150		1		0		150 B
150		1		1		150 B
150		1		1000		0.15 KB
150		1		1000000		0 MB
150		1		1000000000		0 GB

1500		1000		0		1.5 MB

321		1000		0		321 KB
321		1000		1		321000 B
321		1000		1000		321 KB
321		1000		1000000		0.32 MB
321		1000		1000000000		0 GB

9		1000000000		0		9 GB
9		1000000000		1		9000000000 B
9		1000000000		1000		9000000 KB
9		1000000000		1000000		9000 MB
9		1000000000		1000000000		9 GB