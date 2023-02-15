#!/bin/bash
count=1
while [ $count -le $1 ]
do 
  title="item"$count
  description="The todo item: "$count 
  curl -d '{"title":"'"$title"'","description":"'"$description"'"}' -H 'Content-Type: application/json'  http://localhost:8080/api/todolist
  echo -e '\n'
  ((count++))
done
