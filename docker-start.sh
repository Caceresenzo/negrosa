docker run -d \
	--name frontend \
	negrosa-frontend

docker run -d \
	--name backend \
	-v /storage/negrosa/:/app/storage \
	negrosa-backend

docker run -d \
	--name gateway \
	-p 80:80 \
	--link frontend \
	--link backend \
	negrosa-gateway
