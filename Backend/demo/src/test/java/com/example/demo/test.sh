#!/bin/bash

BASE_URL="http://localhost:8080/api/banque"

echo "=== Création du compte Jean Dupont ==="
RESP1=$(curl -s -X POST $BASE_URL/comptes \
-H "Content-Type: application/json" \
-d '{
  "nom": "Jean Dupont",
  "niu": "NIU12345",
  "telephone": "699999999",
  "type": "STUDENT",
  "password": "motDePasseFort",
  "accountNumber": "0000000000000000"
}')
ACC1=$(echo $RESP1 | jq -r '.accountNumber')
echo "AccountNumber Jean Dupont: $ACC1"

echo "=== Création du compte Jean Dubois ==="
RESP2=$(curl -s -X POST $BASE_URL/comptes \
-H "Content-Type: application/json" \
-d '{
  "nom": "Jean Dubois",
  "niu": "NIU12346",
  "telephone": "699999999",
  "type": "STUDENT",
  "password": "motDePasseFort",
  "accountNumber": "0000000000000000"
}')
ACC2=$(echo $RESP2 | jq -r '.accountNumber')
echo "AccountNumber Jean Dubois: $ACC2"

echo "=== Login Jean Dupont ==="
curl -s -X POST $BASE_URL/comptes/login \
-H "Content-Type: application/json" \
-d "{\"num\":\"$ACC1\",\"tel\":\"699999999\",\"pass\":\"motDePasseFort\"}" | jq .

echo "=== Login Jean Dubois ==="
curl -s -X POST $BASE_URL/comptes/login \
-H "Content-Type: application/json" \
-d "{\"num\":\"$ACC2\",\"tel\":\"699999999\",\"pass\":\"motDePasseFort\"}" | jq .

echo "=== Transaction: Dupont -> Dubois (100) ==="
curl -s -X POST $BASE_URL/comptes/$ACC1/transactions/initiate \
-H "Content-Type: application/json" \
-d "{
  \"accountNumber\": \"$ACC1\",
  \"destinatorAccountNumber\": \"$ACC2\",
  \"amount\": 100.00,
  \"type\": \"VIREMENT\"
}" | jq .

echo "=== Transactions Jean Dupont ==="
curl -s -X GET $BASE_URL/comptes/$ACC1/transactions | jq .

echo "=== Transactions Jean Dubois ==="
curl -s -X GET $BASE_URL/comptes/$ACC2/transactions | jq .
