FROM postgres:latest
COPY init.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgre
ENV POSTGRES_DB=KartingRMSpecialDay
EXPOSE 5432