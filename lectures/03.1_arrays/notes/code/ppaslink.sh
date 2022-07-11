#!/bin/sh
DoExitAsm ()
{ echo "An error occurred while assembling $1"; exit 1; }
DoExitLink ()
{ echo "An error occurred while linking $1"; exit 1; }
OFS=$IFS
IFS="
"
/usr/bin/ld        -x   -multiply_defined suppress -L. -o bidon `cat link66440.res` -filelist linkfiles66440.res
if [ $? != 0 ]; then DoExitLink ; fi
IFS=$OFS
