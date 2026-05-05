// See https://aka.ms/new-console-template for more information
Console.WriteLine("Hello, World!");


using MongoDB . Bson ;
using MongoDB . Bson . Serialization . Attributes ;

namespace MongoTest . Models
{
    [ B sonIgnoreEx traElements ]
    public class Etterem
    {
        [ BsonId ]
        public ObjectId Id { get ; set ; }
        public required string nev { get ; set ; }
        public required Cim cim { get ; set ; }
        public required int csillag { get ; set ; }
    }
}