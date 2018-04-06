array=($(grep -e "  int"  jcclexer/JavaParserConstants.java | sort -n -k4 | grep -v DEFAULT | grep -v IN_FORMAL_COMMENT | grep -v IN_MULTI_LINE_COMMENT | cut -d' ' -f 4))

for i in ${array[*]}
do
  type=$(grep -w $i example_tokenid.txt | cut -d, -f 2  | cut -d \" -f 2)
  counter=$(grep -w "$i =" jcclexer/JavaParserConstants.java | cut -d = -f 2 | cut -d ' ' -f 2 | cut -d \; -f 1)
  if [ -n "$type" ]; then
    echo new SJTokenId\(\"$i\"\, \"$type\"\, $counter\)\,
    #echo $i $type
  else
    type=$(grep -w $i my_new_tokens.txt | cut -d, -f 2  | cut -d \" -f 2)
    if [ -n "$type" ]; then
      echo new SJTokenId\(\"$i\"\, \"$type\"\, $counter\)\,
    else
      type="keyword"
      echo new SJTokenId\(\"$i\"\, \"$type\"\, $counter\)\,
    fi
  fi
done
