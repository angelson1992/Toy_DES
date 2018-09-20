# Toy_DES

Some things to note if one wants to run this program. Some of the features of Java 9 were used in it's making so that is the minimum jre
required to run it. If one wants to run the program without all of the debugging output, there is a variable named verboseTest at the beginning that can be set to false. It is also safe to ignore everything in the if(false){...} block of the main function because it is all random unit testing.

The encrypt function successfully takes a Byte object and encrypts it. This new byte currently gets stored into an int because java's Byte object only converts 7 bits of data for reasons I do not understand. The decrpyt function can take bytes casted to ints as cyphertext and return the plaintext, again as a byte casted into an int.

PS. The way to get the cypher to encrypt is to pass true into the isEncryptionMode parameter of it's encryptAndDecrypt method. False for decrypting.
