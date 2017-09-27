<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello World!</title>
</head>
<body>
<#list context.activities as activity>
    ${activity.name}
</#list>
</body>
</html>
