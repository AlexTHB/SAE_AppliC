<?php
// URL de l'API
$api_url = "http://10.3.122.89:8080/packetCaptures";

// Récupérer les données de l'API
$data = file_get_contents($api_url);

// Vérifier si la requête a réussi
if ($data === false) {
    die('Erreur lors de la récupération des données de l\'API');
}

// Convertir les données JSON en un tableau PHP
$decoded_data = json_decode($data, true);

// Vérifier si la conversion a réussi
if ($decoded_data === null) {
    die('Erreur lors de la conversion des données JSON');
}

// Afficher les données au format JSON
header('Content-Type: application/json');
echo json_encode($decoded_data, JSON_PRETTY_PRINT);
?>

