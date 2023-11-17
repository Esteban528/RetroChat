#git pull
docker image rm retrochatserver:1 -f
docker build -t retrochatserver:1 .
docker compose up
