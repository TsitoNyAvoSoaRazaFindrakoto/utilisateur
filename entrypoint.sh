#!/bin/sh
set -e

# Charger les variables d'environnement depuis le fichier .env s'il existe
if [ -f "/app/.env" ]; then
    export $(grep -v '^#' /app/.env | xargs)
fi

echo "Waiting for database..."
until PGPASSWORD="$DB_PASSWORD" psql -U "$DB_USER" -h "$DB_HOST" -d "$DB_NAME" -c '\q' 2>/dev/null; do
  sleep 2
done

echo "Database is ready."

if [ -f "/app/init.sql" ]; then
    echo "Executing init.sql..."
    PGPASSWORD="$DB_PASSWORD" psql -U "$DB_USER" -h "$DB_HOST" -d "$DB_NAME" -f /app/init.sql
    echo "init.sql executed successfully."
fi
