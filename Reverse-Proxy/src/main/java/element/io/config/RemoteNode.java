package element.io.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
@Data
@AllArgsConstructor
public class RemoteNode {

	private String host;


	private Integer port;


}
