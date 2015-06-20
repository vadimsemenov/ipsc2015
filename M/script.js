/**
 *
 * Created by vadim on 20/06/15.
 */
var map = {};
var cnt = 0;
var need = 1001;

println("hello");

function put(str) {
    if (typeof (str) != "number") return;
    var value = eval(str);
    if (0 <= value && value <= 1000 && !(value in map)) {
        map[value] = str;
        ++cnt;
        //println(value);
        //printlnd(cnt);
        if (cnt == need) {
            println("YEESSS!")
            writeAll();
        }
    }
}

function writeAll() {
    for (var i = 0; i <= 1000; ++i) {
        println(map[i]);
    }
}