package so.a56212874
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FirstParam

def int foo(int num, @ClosureParams(FirstParam) Closure closure) {
	def sum = num
	sum += closure.call(5)
}

def int bar(int num, Closure closure) {
	def sum = num
	sum += closure.call(5)
}

//println bar(12) { it * 20 }
//println bar(12) { it + "aaa" }

println foo(12) { it * 20 }
println foo(12) { it + "aaa" }

