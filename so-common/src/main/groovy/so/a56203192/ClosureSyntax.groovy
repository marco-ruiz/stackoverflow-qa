package so.a56203192
import java.time.LocalDateTime

def handleWithClosure(data1, data2, closure) {
	closure("$data1. $data2")
}

def logger = { println "${LocalDateTime.now()} - $it" }
handleWithClosure(1, 'All within parenthesis', logger)
handleWithClosure 2, 'All without parenthesis', logger
handleWithClosure(3, 'List of arguments within parenthesis and closure outside') { logger(it) }

def handleMapWithClosure(map, closure) {
	handleWithClosure(map['num'], "[Named Arguments/Map based] ${map['msg']}", closure)
}

handleMapWithClosure(msg: 'All within parenthesis', num: 1, logger)
handleMapWithClosure msg: 'All without parenthesis', num: 2, logger
handleMapWithClosure(msg: 'List of arguments within parenthesis and closure outside', num: 3) { logger(it) }
