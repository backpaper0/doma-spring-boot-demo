SELECT
  id,
  name
FROM reservation
WHERE name LIKE /* @prefix(name) */'spring%' ESCAPE '$'
ORDER BY name ASC