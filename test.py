import urllib.request
import json

BASE = "http://localhost:8080/api/auth"

# Register
data = json.dumps({
    "name": "Test Patient",
    "email": "patient@test.com",
    "password": "123456",
    "role": "PATIENT",
    "phone": "9999999999",
    "age": 25,
    "address": "Chennai"
}).encode()

req = urllib.request.Request(BASE + "/register",
    data=data,
    headers={"Content-Type": "application/json"},
    method="POST")

try:
    res = urllib.request.urlopen(req)
    print("REGISTER:", res.read().decode())
except Exception as e:
    print("REGISTER ERROR:", e)

# Login
data2 = json.dumps({
    "email": "patient@test.com",
    "password": "123456"
}).encode()

req2 = urllib.request.Request(BASE + "/login",
    data=data2,
    headers={"Content-Type": "application/json"},
    method="POST")

try:
    res2 = urllib.request.urlopen(req2)
    print("LOGIN:", res2.read().decode())
except Exception as e:
    print("LOGIN ERROR:", e)
