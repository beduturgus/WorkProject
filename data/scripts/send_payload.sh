#!/usr/bin/env bash
curl -i --header "Accept:application/json" -b /Users/benas/Notes/security/cookies.txt -d '<contactsInfo>
  <name>Zenius</name>
  <company>Zenitech</company>
  <phone>123</phone>
  <empty></empty>
  <filler>kazkas kitas</filler>
</contactsInfo>' http://localhost:8089/api/postNothing
