#!/bin/zsh
alias setclip="xclip -selection c"
./create_token_id.sh | setclip
