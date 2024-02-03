# Message producer

Wanneer de artemis docker container draait, kan je erop inloggen en via de command line interface messages op een queue zetten.

#### Open een shell op de docker container
> docker exec -it jms-async-artemis-1 bash

#### Verstuur messages
> ./artemis producer --user admin --password superman --destination queue://LocalSpringboot::numbers --message hello --message-count 100
