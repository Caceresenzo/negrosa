docker build -t negrosa-base -f docker/Dockerfile.base .
docker build -t negrosa-frontend -f frontend/Dockerfile frontend
docker build -t negrosa-backend -f backend/Dockerfile backend
docker build -t negrosa-gateway -f docker/Dockerfile.gateway docker