#!/bin/bash
sudo apt-get update
sudo apt-get install postgresql-10.4
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
sudo -u postgres psql -c "CREATE DATABASE mydb;"
sudo service postgresql start