/*
 *   Copyright 2012-2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package pl.hycom.pip.messanger.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by patry on 18/03/2017.
 */

@Data
@Entity
@Table(name = "KEYWORDS")
@NoArgsConstructor
public class Keyword implements Serializable {

    private static final long serialVersionUID = -8450849049742048985L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String word;

    public Keyword(String word) {
        super();
        this.word = word;
    }

}