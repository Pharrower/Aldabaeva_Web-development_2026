# Примеры API контрактов

## Получение списка животных
`GET /api/animals`

**Response (JSON):**
```json
[
  {
    "id": 1,
    "name": "Дружок",
    "species": "Собака",
    "status": "AVAILABLE"
  }
]
```

## Подача заявки на адопцию
`POST /api/requests`

**Request Body**
```json
{
  "animalId": 1,
  "message": "Хочу забрать этого пса!"
}
```