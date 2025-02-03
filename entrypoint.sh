#!/bin/sh
set -e

echo "Waiting for database..."
until PGPASSWORD=fifaliana psql -U postgres -h utilisateur-database -d utilisateur -c '\q' 2>/dev/null; do
  sleep 2
done

echo "Database is ready."

if [ -f "/app/init.sql" ]; then
    echo "Executing init.sql..."
    PGPASSWORD=fifaliana psql -U postgres -h utilisateur-database -d utilisateur -f /app/init.sql
    echo "init.sql executed successfully."
fi

exec java -jar application.jar
