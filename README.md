# thesaurus-transformer
thesaurus transformer for the endeca projects with XM enabled
##### JSON to CSV transformer (to make it a easily readable csv by a business user)
> JSONtoCSVUtility - has a main method to create the desired csv which then can be read by the business user
> 
> the program takes the input from the  $project_path/src/main/resources/endeca-thesaurus.json
>> a sampe file is already part of the repo
>
> the program writes the output to the $project_path/output/thesaurus.csv
    
##### CSV to JSON transformer (to re-import back to the Endeca)
> CSVToJSONUtility - has a main method to create the json which then can be imported to endeca
> 
> the program takes the input from the  $project_path/src/main/resources/thesaurus.csv
>> a sampe file is already part of the repo
>
> the program writes the output to the $project_path/output/thesaurus.json