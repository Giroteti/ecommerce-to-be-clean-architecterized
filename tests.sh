#!/usr/bin/env bash


usage() { echo "Usage: $0 [-u unit tests only] [-i integration tests only] [-h help]" 1>&2; exit 1; }

while getopts "uih" option; do
    case "${option}" in
        u)
            ./mvnw clean test -Dgroups="com.exo.ecommerce.FastTests" -e
            exit 0
            ;;
        i)
            ./mvnw clean test -Dgroups="com.exo.ecommerce.SlowTests" -e
            exit 0
            ;;
        h)
            usage
            ;;
        *)
            usage
            ;;
    esac
done
./mvnw clean test -e
