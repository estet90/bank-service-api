# Сборка приложения
<p>`mvn package` - сборка с прогоном тестов</p>
<p>`mvn package -Dmaven.test.skip=true` - сборка без прогона тестов</p>

# Запуск
<p>`java -jar ./target/bank-services-api-<версия приложения>.jar`</p>

# Доступные сервисы
## API приложения
<p>`http://localhost:8080/bank-services/api/contacts/{contactId}/applications/last`</p>
## Документация
<p>`http://localhost:8080/bank-services/api/services` - ссылки на документацию по API</p>
<p>`http://localhost:8080/bank-services/api/?_wadl` - WADL</p>
<p>`http://localhost:8080/bank-services/api/api-docs?url=/bank-services/api/swagger.json` - сваггер</p>
