import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder


def Reading() {
    def qq
    Scanner s = new Scanner(System.in)
    qq = s.nextLine()
    s.close()
    return qq
}

SumResult = 0d
MulResult = 0
SortedInputs = []


def Calculation(ArrayList Sums, int K, ArrayList Muls){
    //SumResult = Sums.sum() * K
    SumResult = Sums.inject(0d) { sum, val -> sum + val } * K
    MulResult = Muls.inject(1.0d) { prod, val -> prod * val }
    SortedInputs = (Sums + Muls).sort()
    println(SumResult)
    println(MulResult)
    println(SortedInputs)
}

def OutputCreator(Double SumResultIn, Double MulResultIn,ArrayList SortedArraysIn, String format){
    if (format == "XML"){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.Output() {
                SumResult(SumResultIn)
                MulResult(MulResultIn)
                SortedInputs{
                    SortedArraysIn.each {
                        decimal(it)
                    }
                }
            }

        println(writer)
    }
    if (format == "Json" ){
        JsonBuilder builder = new JsonBuilder()

        builder {
            SumResult(SumResultIn)
            MulResult(MulResultIn)
            SortedInputs (
                SortedArraysIn.each {
                    it
                }
            )

            }

        String json = JsonOutput.prettyPrint(builder.toString())
        println(json)
    }
}

inputFormat = System.in.newReader().readLine()
//println(inputFormat)
if (inputFormat != "XML" && inputFormat != "Json") {
    println("Enter correct format: XML or Json")
    inputFormat = System.in.newReader().readLine()
}
    else {
    inputString = Reading()
}
//XML section
if (inputFormat == "XML"){
def parser = new XmlSlurper()
def str = parser.parseText(inputString)
def coefK = Integer.parseInt(str.K.text())
def sums = str.Sums.decimal.collect{ it.text() as Double }
def muls = str.Muls.int.collect(){ it.text() as Double}
    println(coefK)
    println(sums.toString())
    println(muls.toString())
Calculation(sums, coefK, muls)
    OutputCreator(SumResult, MulResult, SortedInputs, 'XML')

}
    //JSON section
else if (inputFormat == "Json"){
    def parser = new JsonSlurper()
    def str = parser.parseText(inputString)
    def coefK = (str.K)
    def sums = str.Sums.collect()
    def muls = str.Muls.collect()
    println(coefK)
    println(sums.toString())
    println(muls.toString())
    Calculation(sums, coefK, muls)
    OutputCreator(SumResult, MulResult, SortedInputs, 'Json')
}


