package so.a56204337
import java.time.LocalDateTime

Map properties = [enable_api_security: false, owner: 'Hello', time: LocalDateTime.now()]

Object enhanced = new Object()
properties.each { key, value -> 
	enhanced.metaClass."$key" = value
}

println enhanced.enable_api_security
println enhanced.owner
println enhanced.time


def COMPONENTS_LIST= "one two three"
Map store = [value: COMPONENTS_LIST]
println store