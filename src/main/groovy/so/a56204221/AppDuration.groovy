package so.a56204221
import groovy.time.*

def startTimeString = "Sat May 18 00:00:00 IST 2019"
def startTime = Date.parse("E MMM dd H:m:s z yyyy", startTimeString)
def endTime = new Date()

use (TimeCategory) {
	TimeDuration duration = endTime - startTime
	println "[${startTimeString}] was [${duration}] ago"
}
