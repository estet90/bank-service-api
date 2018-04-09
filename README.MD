# Сборка приложения
`mvn package` - сборка с прогоном тестов
`mvn package -Dmaven.test.skip=true` - сборка без прогона тестов

# Запуск
`java -jar ./target/bank-services-api-<версия приложения>.jar`

# Доступные сервисы
## API приложения
`http://localhost:8080/bank-services/api/contacts/{contactId}/applications/last`
## Документация
`http://localhost:8080/bank-services/api/services` - ссылки на документацию по API
`http://localhost:8080/bank-services/api/?_wadl` - WADL
`http://localhost:8080/bank-services/api/api-docs?url=/bank-services/api/swagger.json` - сваггер
