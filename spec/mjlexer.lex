package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%


// Whitespaces

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }


// Reserved keywords

<YYINITIAL> "program" 		{ return new_symbol(sym.PROGRAM, yytext()); }
<YYINITIAL> "namespace"		{ return new_symbol(sym.NAMESPACE, yytext()); }
<YYINITIAL> "read" 			{ return new_symbol(sym.READ_, yytext()); }
<YYINITIAL> "print" 		{ return new_symbol(sym.PRINT, yytext()); }
<YYINITIAL> "void" 			{ return new_symbol(sym.VOID, yytext()); }
<YYINITIAL> "if" 			{ return new_symbol(sym.IF, yytext()); }
<YYINITIAL> "else" 			{ return new_symbol(sym.ELSE, yytext()); }
<YYINITIAL> "const" 		{ return new_symbol(sym.CONST, yytext()); }
<YYINITIAL> "extends"		{ return new_symbol(sym.EXTENDS, yytext()); }
<YYINITIAL> "return"		{ return new_symbol(sym.RETURN, yytext()); }
<YYINITIAL> "break" 		{ return new_symbol(sym.BREAK_, yytext()); }
<YYINITIAL> "class" 		{ return new_symbol(sym.CLASS, yytext()); }
<YYINITIAL> "new" 			{ return new_symbol(sym.NEW_, yytext()); }
<YYINITIAL> "continue" 		{ return new_symbol(sym.CONTINUE, yytext()); }
<YYINITIAL> "for" 			{ return new_symbol(sym.FOR, yytext()); }
<YYINITIAL> "static" 		{ return new_symbol(sym.STAT, yytext()); }
<YYINITIAL> "goto" 		{ return new_symbol(sym.GOTO, yytext()); }


// Operators

<YYINITIAL> "[" 			{ return new_symbol(sym.LSQBRACE, yytext()); }
<YYINITIAL> "]" 			{ return new_symbol(sym.RSQBRACE, yytext()); }
<YYINITIAL> "(" 			{ return new_symbol(sym.LPAREN, yytext()); }
<YYINITIAL> ")" 			{ return new_symbol(sym.RPAREN, yytext()); }
<YYINITIAL> "{" 			{ return new_symbol(sym.LBRACE, yytext()); }
<YYINITIAL> "}"				{ return new_symbol(sym.RBRACE, yytext()); }
<YYINITIAL> "==" 			{ return new_symbol(sym.SAME, yytext()); }
<YYINITIAL> "!=" 			{ return new_symbol(sym.NSAME, yytext()); }
<YYINITIAL> "<" 			{ return new_symbol(sym.LESS, yytext()); }
<YYINITIAL> ">" 			{ return new_symbol(sym.GREATER, yytext()); }
<YYINITIAL> ">=" 			{ return new_symbol(sym.GREQ, yytext()); }
<YYINITIAL> "<=" 			{ return new_symbol(sym.LEQ, yytext()); }
<YYINITIAL> "+" 			{ return new_symbol(sym.PLUS, yytext()); }
<YYINITIAL> "-" 			{ return new_symbol(sym.MINUS, yytext()); }
<YYINITIAL> "*" 			{ return new_symbol(sym.MUL, yytext()); }
<YYINITIAL> "/" 			{ return new_symbol(sym.DIV, yytext()); }
<YYINITIAL> "%" 			{ return new_symbol(sym.MOD, yytext()); }
<YYINITIAL> "++" 			{ return new_symbol(sym.INC, yytext()); }
<YYINITIAL> "--" 			{ return new_symbol(sym.DEC, yytext()); }
<YYINITIAL> "&&" 			{ return new_symbol(sym.AND, yytext()); }
<YYINITIAL> "||" 			{ return new_symbol(sym.OR, yytext()); }
<YYINITIAL> "=" 			{ return new_symbol(sym.EQUAL, yytext()); }
<YYINITIAL> "=>" 			{ return new_symbol(sym.ARROW, yytext()); }
<YYINITIAL> ":" 			{ return new_symbol(sym.COLON, yytext()); }
<YYINITIAL> "." 			{ return new_symbol(sym.DOT, yytext()); }
<YYINITIAL> ";" 			{ return new_symbol(sym.SEMI, yytext()); }
<YYINITIAL> "," 			{ return new_symbol(sym.COMMA, yytext()); }

<YYINITIAL> "@" 			{ return new_symbol(sym.MONKEY, yytext()); }
<YYINITIAL> "#" 			{ return new_symbol(sym.HASH, yytext()); }


// Comments

<YYINITIAL> "//" 			{ yybegin(COMMENT); }
<COMMENT> .      			{ yybegin(COMMENT); }
<COMMENT> "\r\n" 			{ yybegin(YYINITIAL); }


// Number value

[0-9]+  					{ return new_symbol(sym.NUMBER, new Integer (yytext())); }


// Boolean value

"true"						{ return new_symbol(sym.BOOL_VALUE, true); } 
"false" 					{ return new_symbol(sym.BOOL_VALUE, false); } 


// Char value

'.' 						{ return new_symbol(sym.CHAR_VALUE, yytext().charAt(1)); }


// Identifier
 
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol (sym.IDENT, yytext()); }


// Error
. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }
