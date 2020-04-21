var bigInt = require("big-integer");
memorizacion = {};
var fibonacci = (function(n){
    if(memorizacion[n]!==undefined){
        return memorizacion[n];
    }else {
        if(n===bigInt.zero){
            return bigInt.zero;
        } else if (n===bigInt.one){
            return bigInt.one;
        } else {
            memorizacion[n] = fibonacci(n-1).add(fibonacci(n-2));
            return memorizacion[n];
        }
    }
});
module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');

    let nth = req.body.nth
    let answer = fibonacci(nth);

    context.res = {
        body: answer.toString()
    };
}