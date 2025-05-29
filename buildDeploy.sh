#!/bin/bash

# Define text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
RESET='\033[0m'  # Reset color

# Exit on error
set -e

LOG_FILE="build_log.txt"

# Show the main menu
show_main_menu() {
  echo -e "${BLUE}MAIN MENU${RESET}"
  echo -e "${YELLOW}Choose an option:${RESET}"
  echo -e "${GREEN}1.${RESET} Build JAR file"
  echo -e "${GREEN}2.${RESET} Docker Tasks"
  echo -e "${GREEN}3.${RESET} Exit"
  read -p "Enter your choice: " main_choice
}

# Show the JAR build menu
show_build_jar_menu() {
  echo -e "${BLUE}BUILD JAR FILE${RESET}"
  echo -e "${YELLOW}Choose a service to build:${RESET}"
  echo -e "${GREEN}1.${RESET} MartAuth"
  echo -e "${GREEN}2.${RESET} MartGateway"
  echo -e "${GREEN}3.${RESET} MartProduct"
  echo -e "${GREEN}4.${RESET} MartEureka"
  echo -e "${GREEN}5.${RESET} All Services"
  read -p "Enter your choice: " jar_choice
}

# Show Docker task menu
show_docker_menu() {
  echo -e "${YELLOW}DOCKER TASKS${RESET}"
  echo -e "${GREEN}1.${RESET} Build Docker Images"
  echo -e "${GREEN}2.${RESET} Deploy Docker Containers"
  echo -e "${GREEN}3.${RESET} Build and Deploy Both"
  read -p "Enter your choice: " docker_choice
}

# Timer for build
track_time() {
  start_time=$(date +%s)
  while true; do
    sleep 1
    elapsed=$(( $(date +%s) - start_time ))
    echo -ne "${YELLOW}Elapsed time: ${elapsed} seconds${RESET}\r"
  done
}

# Build one service
build_service() {
  service=$1
  echo -e "${GREEN}Building $service...${RESET}"
  cd "$service" || { echo -e "${RED}Directory $service not found${RESET}"; exit 1; }

  track_time & timer_pid=$!
  trap "kill $timer_pid 2>/dev/null" EXIT

  mvn clean install -DskipTests > "../$LOG_FILE" 2>&1

  kill $timer_pid 2>/dev/null
  trap - EXIT

  echo -e "\n${GREEN}$service build completed.${RESET}"
  cd ..
}

# Docker task handler
handle_docker() {
  case $docker_choice in
    1) echo -e "${GREEN}Building Docker images...${RESET}"; docker-compose build ;;
    2) echo -e "${GREEN}Deploying Docker containers...${RESET}"; docker-compose up -d ;;
    3) echo -e "${GREEN}Building and deploying Docker...${RESET}"; docker-compose build && docker-compose up -d ;;
    *) echo -e "${RED}Invalid Docker choice.${RESET}"; exit 1 ;;
  esac
}

# Main flow
while true; do
  show_main_menu

  case $main_choice in
    1)
      show_build_jar_menu
      case $jar_choice in
        1) build_service "MartAuth" ;;
        2) build_service "MartGateway" ;;
        3) build_service "MartProduct" ;;
        4) build_service "MartEureka" ;;
        5)
          for svc in MartAuth MartGateway MartProduct MartEureka; do
            build_service "$svc"
          done
          ;;
        *) echo -e "${RED}Invalid build choice.${RESET}" ;;
      esac
      ;;
    2)
      show_docker_menu
      handle_docker
      ;;
    3)
      echo -e "${GREEN}Exiting. Bye!${RESET}"
      exit 0
      ;;
    *)
      echo -e "${RED}Invalid choice in main menu.${RESET}"
      ;;
  esac
done
