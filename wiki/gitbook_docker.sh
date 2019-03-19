

#alias gitbook="docker run --rm -v /anuz/dev/enoa/enoa/wiki/zh_TW:/gitbook -p 4000:4000 billryan/gitbook gitbook"

alias gitbook="docker run --rm -v $PWD:/gitbook -p 4000:4000 billryan/gitbook gitbook"
