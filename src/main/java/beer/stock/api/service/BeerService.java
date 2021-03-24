package beer.stock.api.service;

import beer.stock.api.dto.BeerDTO;
import beer.stock.api.entity.Beer;
import beer.stock.api.exceptions.BeerAlreadyRegisteredException;
import beer.stock.api.exceptions.BeerNotFoundException;
import beer.stock.api.exceptions.BeerStockExceededException;
import beer.stock.api.mapper.BeerMapper;
import beer.stock.api.repository.BeerRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Uellington Damasceno
 */
@Service
public class BeerService {

    private BeerRepository beerRepository;
    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        this.verifyIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.toModel(beerDTO);
        beer = beerRepository.save(beer);
        return beerMapper.toDTO(beer);
    }

    public BeerDTO findByName(String name) throws BeerNotFoundException {
        Beer foundBeer = beerRepository.findByName(name)
                .orElseThrow(() -> new BeerNotFoundException(name));
        return beerMapper.toDTO(foundBeer);
    }

    public List<BeerDTO> listAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(toList());
    }

    public void deleteById(Long id) throws BeerNotFoundException {
        this.verifyIfExist(id);
        beerRepository.deleteById(id);
    }

    public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {
        Beer beerToIncrementStock = verifyIfExist(id);
        int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();

        if (quantityAfterIncrement > beerToIncrementStock.getMax()) {
            throw new BeerStockExceededException(id, quantityToIncrement);
        }

        beerToIncrementStock.setQuantity(quantityAfterIncrement);
        Beer savedBeer = beerRepository.save(beerToIncrementStock);
        return beerMapper.toDTO(savedBeer);
    }

    private Beer verifyIfExist(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));
    }

    private void verifyIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
        if (beerRepository.findByName(name).isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }
    }

}
