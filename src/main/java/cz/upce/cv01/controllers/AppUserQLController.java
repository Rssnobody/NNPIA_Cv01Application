package cz.upce.cv01.controllers;

import cz.upce.cv01.dto.AppUserInputQLDto;
import cz.upce.cv01.dto.AppUserOutputDto;
import cz.upce.cv01.services.AppUserService;
import cz.upce.cv01.services.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


/*
* N+1 problem GraphQL:
* - GraphQL server provádí mnoho zbytečných dotazů při načítání vnořených dat,
* konkrétně při dotazech vracejících kolekce objektů - pro každý jeden objekt kolekce
*  je proveden totiž další samostatný dotaz na server
* -> toto vede k velkému množství dotazů, zatížení a a případnému zpomalení aplikace
*
* Anotace @BatchMapping řeší tento problém. Při zpracování metody s anotací @BatchMapping
*  GraphQL server počká, až se všechny objekty v kolekci vrátí, a poté je zpracuje najednou,
*  což umožní vyhnout se problému N+1 dotazů.
*
* @SchemaMapping se používá pro definici resolveru, který řeší konkrétní dotaz na GraphQL schéma.
*  Tato anotace se používá v situacích, kdy chceme vyřešit dotaz na GraphQL schéma,
*  který nevyžaduje více dotazů na server.
*
* @BatchMapping se používá pro řešení situací, kdy je potřeba získat více dat z různých zdrojů,
*  a to takovým způsobem, aby se minimalizoval počet dotazů na server. Tato anotace se používá
*  na metody, které vrací kolekce objektů, a umožňuje vytvořit tzv. batch loader, který zpracuje
*  všechna data najednou a sníží počet dotazů na server.
*
* Pro správnou funkci anotace @SchemaMapping je třeba vytvořit resolver metodu, která odpovídá
*  definici dotazu v GraphQL schématu. Tato metoda musí být správně anotovaná a musí vracet
* objekt, který odpovídá typu definovanému v GraphQL schématu.
*
* Pro správnou funkci anotace @BatchMapping je třeba vytvořit metodu, která vrací kolekci objektů,
*  a tuto metodu správně anotovat pomocí anotace @BatchMapping. Tato metoda musí také vytvořit
*  a použít batch loader pro získání dat z různých zdrojů a musí vracet kolekci objektů.
*
* Dále je nutné pro funkci obou anotací dodržet správný typ a strukturu dat v rámci GraphQL schématu.
*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*
* GraphQL subscription je funkce umožňující klientům odebírat data v reálném čase. To znamená,
*  že klienti mohou být automaticky informováni o jakékoli změně dat na serveru bez nutnosti
*  opakovaně posílat požadavky na aktualizaci.
*
* a. Komunikace v rámci GraphQL subscription probíhá na základě protokolu WebSockets, což znamená,
*  že je založena na dvoucestné komunikaci mezi klientem a serverem. Klient odebírá data ze serveru,
*  který je odesílá pomocí WebSocketů. V tomto případě je server odesílatelem a klient příjemcem.
*
* b. Anotace @SubscriptionMapping
*
* c. GraphQL subscription lze využít v mnoha aplikacích, například v chatovací aplikaci,
*  kde se nové zprávy zobrazují v reálném čase, nebo v aplikaci pro sledování akciového trhu,
*  kde se změny na burze odebírají v reálném čase.
*
* d. Web socket je protokol umožňující dvoucestnou komunikaci mezi klientem a serverem.
*  GraphQL subscription používá WebSockets pro přenos dat v reálném čase mezi klientem a serverem.
*  Tímto způsobem klient může odebírat data z GraphQL serveru bez nutnosti stále neustále posílat
*  požadavky na aktualizaci. WebSocket je tedy důležitou součástí implementace GraphQL subscription.
*
*/
@Controller
@AllArgsConstructor
public class AppUserQLController {
    private final AppUserService appUserService;

    @QueryMapping
    public AppUserOutputDto appUser(@Argument Long id) throws ResourceNotFoundException {
        return appUserService.findUserById(id).toDto();
    }

    @MutationMapping
    public AppUserOutputDto createAppUser(@Argument AppUserInputQLDto appUser) {
        return appUserService.create(appUser.toEntity())
                .toDto();
    }
}
