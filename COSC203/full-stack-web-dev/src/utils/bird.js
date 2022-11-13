// string_normalize: strips diacritics and converts to lower case
function string_normalize(s) {
    return s.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
}


const status_values = {
    "Not Threatened": 0,
    "Naturally Uncommon": 1,
    "Relict": 2,
    "Recovering": 3,
    "Declining": 4,
    "Nationally Increasing": 5,
    "Nationally Vulnerable": 6,
    "Nationally Endangered": 7,
    "Nationally Critical": 8,
    "Data Deficient": 9
}

const buildAggregationPipeline = (search, sort, status) => {
    const pipeline = []

    if(search){
        const regex = {
            $regex: search,
            $options: 'i'
        }
        const searchFields = ["primary_name", "english_name", "scientific_name", "other_names", "order", "family"]
        $or = []

        searchFields.forEach(searchField => $or.push({[searchField]: regex}))

        pipeline.push({$match: {$or}})
    }

    if(sort){
        let $sort;
        switch (sort) {
            case "Alphabetical":
                $sort = {'primary_name': 1}; break;
            case "Reverse alphabetical":
                $sort = {'primary_name': -1}; break;
            case "Shortest to longest":
                $sort = {'size.length.value': 1}; break;
            case "Longest to shortest":
                $sort = {'size.length.value': -1}; break;
            case "Lightest to heaviest":
                $sort = {'size.weight.value': 1}; break;
            case "Heaviest to lightest":
                $sort = {'size.weight.value': -1}; break;
            case "Endangered to unthreatened":
                console.log("g")
                $sort = {'status_value': 1}; break;
            case "Unthreatened to endangered":
                $sort = {'status_value': -1}; break;
        }

        if($sort){
            pipeline.push({$sort})
        }

    }

    if(status && status !== "All"){
        pipeline.push({$match: {status}})
    }

    pipeline.push({$skip: 0})

    return pipeline
}

module.exports = {status_values, string_normalize,  buildAggregationPipeline}
