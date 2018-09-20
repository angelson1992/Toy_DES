# Toy_DES

Some things to note if one wants to run this program. Some of the features of Java 9 were used in it's making so that is the minimum jre
required to run it. If one wants to run the program without all of the debugging output, there is a variable named verboseTest at the beginning that can be set to false. It is also safe to ignore everything in the if(false){...} block of the main function because it is all random unit testing.

The encrypt function successfully takes a Byte object and encrypts it. This new byte currently gets stored into an int because java's Byte object only converts 7 bits of data for reasons I do not understand. The decrpyt function can take bytes casted to ints as cyphertext and return the plaintext, again as a byte casted into an int.

PS. The way to get the cypher to encrypt is to pass true into the isEncryptionMode parameter of it's encryptAndDecrypt method. False for decrypting.

As for implementation details.
I pretty much implemented the Toy DES to the diagram. I did use a java class called BitSet that allowed me to interact with bits in a friendlier and more readable manner. I would assume that this likely means that it isn't a fast or memory efficient as the Toy DES would normally be with pure bit arithmetic. I would say that it shouldn't have any effect on the security level of Toy DES.

If I had to compare my notions of security between the toy DES and the true DES, I would say that the former is laughably less secure. Between the lessened number of feistel rounds, imbalanced s-boxes, and only having two s-boxes, it would have to be leaking more information about the key than the original DES.
