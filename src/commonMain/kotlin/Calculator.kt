import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.parser.Parser

sealed class AST {
    data class Number(val n: Double) : AST()
    data class Add(val a: AST, val b: AST) : AST()
    data class Sub(val a: AST, val b: AST) : AST()
    data class Mul(val a: AST, val b: AST) : AST()
    data class Div(val a: AST, val b: AST) : AST()
}

object Parser : Grammar<AST>() {
    val number by token("""-?\d+(\.\d+)?""")
    val whiteSpace by token("""\s+""", ignore = true)
    val plus by token("""\+""")
    val minus by token("""-""")
    val times by token("""[*×]""")
    val div by token("""/""")
    val leftBracket by token("""\(""")
    val rightBracket by token("""\)""")

    val bracedTerm by -leftBracket * parser(this::addSubTerm) * -rightBracket
    val seed: Parser<AST> by number map { AST.Number(it.text.toDouble()) } or bracedTerm
    val mulDivTerm by leftAssociative(seed, times or div use { type }) { a, op, b ->
        if (op == times) AST.Mul(a, b) else AST.Div(a, b)
    }
    val addSubTerm by leftAssociative(mulDivTerm, plus or minus use { type }) { a, op, b ->
        if (op == plus) AST.Add(a, b) else AST.Sub(a, b)
    }

    override val rootParser: Parser<AST> by addSubTerm
}
