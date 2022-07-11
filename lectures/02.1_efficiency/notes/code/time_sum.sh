CSV_FILE=duration.csv
echo "#run,c,py" > "${CSV_FILE}"
export TIMEFORMAT="%E"
for i in {1..100}   # you can also use {0..9}
do
    c_duration=$((time ./a.out) 2>&1 > /dev/null)
    py_duration=$((time python3 sum.py) 2>&1 > /dev/null)
    echo "$i,${c_duration},${py_duration}" >> "${CSV_FILE}"
done
echo "Done."
