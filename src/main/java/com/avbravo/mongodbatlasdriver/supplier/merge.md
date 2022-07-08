# Merge
https://www.mongodb.com/docs/manual/reference/operator/aggregation/lookup/

Leer en el documento
Perform Multiple Joins and a Correlated Subquery with $lookup


## Lookup normal
db.pais.aggregate(
[
  
   {
    $lookup:{
            from:"planeta",
            localField:"planeta.idplaneta",
            foreignField:"idplaneta",
            as:"planeta"
            }
    },
     {
      $replaceRoot: { newRoot: { $mergeObjects: [ { $arrayElemAt: [ "$planeta", 0 ] }, "$$ROOT" ] } }
   },
   { $project: { planeta: 0 } }

]
).pretty()


## Simple
```
db.profesion.aggregate(
[  
   {
    $lookup:{
            from:"grupoprofesion",
            localField:"grupoprofesion.idgrupoprofesion",
            foreignField:"idgrupoprofesion",
            as:"grupoprofesion"
            }
    }
,
     {
      $replaceRoot: { newRoot: { $mergeObjects: [ { $arrayElemAt: [ "$grupoprofesion", 0 ] }, "$$ROOT" ] } }
   },
   { $project: { grupoprofesion: 0 } }
]
).pretty()
```
Genera
```
{
        "_id" : ObjectId("62c6ea0c217f3a2199d05dba"),
        "idgrupoprofesion" : "informatica",
        "idprofesion" : "1",
        "profesion" : "Desarrollador"
}
{
        "_id" : ObjectId("62c6ea21217f3a2199d05dbb"),
        "idgrupoprofesion" : "medicina",
        "idprofesion" : "2",
        "profesion" : "Cirujano"
}
```

## Dos
```
db.users.aggregate(
  [
    {
      $project:
        { 
          user: { $mergeObjects: [ "$name", "$contact" ] }
        }
    }
  ]
).pretty()
```
```
db.profesion.aggregate(
[  
   {
    $lookup:{
            from:"grupoprofesion",
            localField:"grupoprofesion.idgrupoprofesion",
            foreignField:"idgrupoprofesion",
            as:"grupoprofesion"
            }
    }
,
     {
      $replaceRoot: { newRoot: { $mergeObjects: [ { $arrayElemAt: [ "$grupoprofesion", 0 ] }, "$$ROOT" ] } }
   },
   { $project: 
   {  
          profesion:
          user: { $mergeObjects: [ "$grupoprofesion" ] }
      } 
}
]
).pretty()
```















MERGE


db.profesion.aggregate( [
   {
      $lookup:
         {
           from: "grupoprofesion",
           let: { order_item: "$grupoprofesion.idgrupoprofesion"},
           pipeline: [
              { $match:
                 { $expr:
                    { $and:
                       [
                         { $eq: [ "$grupoprofesion",  "$$order_item" ] }
                        
                       ]
                    }
                 }
              },
              { $project: { idgrupoprofesion: 0, _id: 0 } }
           ],
           as: "grupoprofesion"
         }
    }
] ).pretty()

genera
{
        "_id" : 3,
        "item" : "cookies",
        "price" : 10,
        "ordered" : 60,
        "stockdata" : [
                {
                        "warehouse" : "A",
                        "instock" : 80
                }
        ]
}
