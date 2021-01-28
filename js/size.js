print("start");
var totalSize = 0;
db.getCollectionNames().forEach(function(name) {
    var iSize = db[name].stats().avgObjSize;
    totalSize =+ iSize;
});
print(totalSize);