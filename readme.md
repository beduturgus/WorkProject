Starting database:

1. Make sure postgreSQL service is running `brew services start postgresql`
2. Start psql server which stores databases `pg_ctl -D /usr/local/var/postgres start`
3. Can list available databases with `psql -l`


After every application relaunch cookie that is required to send requests to this application must be refreshed by running 1 or 2:
1. Run: `curl -i -X POST -d username=user -d password=userPass -c /Users/benas/Notes/security/cookies.txt  http://localhost:8089/login`

2. Execute refresh_cookie.sh

