<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <div>
                    <xsl:apply-templates />
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="BlastOutput">
        <table style="width: 100%; white-space: nowrap; background-color: #66CCFF;">
            <tr>
                <td colspan="3" style="text-align: left; font-size: xx-large;">
        Sequence Similarity Report
                </td>
            </tr>
            <tr>
                <td style="text-align: right; font-size: large;">Search Program:</td>
                <td>
                    <xsl:value-of select="BlastOutput_program"/>
                    <xsl:value-of select="BlastOutput_version"/>
                </td>
                <td style="width: 100%">
                </td>
            </tr>
            <xsl:apply-templates select="BlastOutput_param"/>
        </table>
        <div style="width: 100%; text-align: center; font-size: x-large; background-color: #66FFCC">
    Detailed Analysis of Results
        </div>
        <xsl:apply-templates select="BlastOutput_iterations"/>
    </xsl:template>

    <xsl:template match="BlastOutput_param">
        <tr>
            <td style="text-align: right; font-size: large;">
      Parameters:
            </td>
            <td style="text-align: left; font-size: medium;">
                <xsl:for-each select="Parameters/*">
                    <xsl:variable name="lname" select="local-name()" />

                    <xsl:if test="$lname='Parameters_matrix'">
            Matrix:
                    </xsl:if>
                    <xsl:if test="$lname='Parameters_expect'">
            Expected:
                    </xsl:if>
                    <xsl:if test="$lname='Parameters_gap-open'">
            gap_open:
                    </xsl:if>
                    <xsl:if test="$lname='Parameters_gap-extend'">
            gap_extend:
                    </xsl:if>

                    <xsl:value-of select="."/>
                </xsl:for-each>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="BlastOutput_iterations/Iteration">
        <div style="width: 100%; background-color: #FFFFCC;">
      Iteration:
            <xsl:value-of select="Iteration_iter-num/."/>
        </div>
        <xsl:apply-templates select="Iteration_hits"/>
    </xsl:template>

    <xsl:template match="Iteration_hits/Hit">
        <div style="width: 100%; background-color: #FFFF99;">
    Hit Id:
            <xsl:value-of select="Hit_id/."/>
            <br/>
            <xsl:value-of select="Hit_def/."/>
            <br/>
    Sequence length of hit =
            <xsl:value-of select="Hit_len/."/>
        </div>

        <xsl:apply-templates select="Hit_hsps"/>
    </xsl:template>

    <xsl:template match="Hit_hsps/Hsp">

        <div style="width: 100%; background-color: #FFEEFF;">
    
    High-scoring segment pair (HSP) group
            <br/>
    Score =
            <xsl:value-of select="Hsp_score/."/>,
    E =
            <xsl:value-of select="Hsp_evalue/."/>,

            <xsl:variable name="length" select="Hsp_align-len/." />
            <xsl:variable name="identity" select="Hsp_identity/." />
            <xsl:variable name="positive" select="Hsp_positive/." />

    Identities =
            <xsl:value-of select="$identity"/>/
            <xsl:value-of select="$length"/>
                 (
            <xsl:value-of select="format-number($identity div $length, '##.#%')"/>),

    Positives =
            <xsl:value-of select="$positive"/>/
            <xsl:value-of select="$length"/>
                 (
            <xsl:value-of select="format-number($positive div $length, '##.#%')"/>),

    Length =
            <xsl:value-of select="Hsp_align-len/."/>

        </div>

        <div style="width: 100%; text-align: left; font-family: courier;">

            <xsl:call-template name='align'>
                <xsl:with-param name='qseq' select='Hsp_qseq/.'/>
                <xsl:with-param name='mseq' select='Hsp_midline/.'/>
                <xsl:with-param name='hseq' select='Hsp_hseq/.'/>
            </xsl:call-template>

        </div>
        <br/>

    </xsl:template>

    <xsl:template name='align'>
        <xsl:param name='qseq'/>
        <xsl:param name='hseq'/>
        <xsl:param name='mseq'/>

        <br/>
        <xsl:if test='string-length($qseq) != 0' >
            <xsl:choose>
                <xsl:when test='string-length($qseq) > 80 '>
                    <xsl:call-template name='align80'>
                        <xsl:with-param name='qseq' select='substring($qseq, 1, 80)'/>
                        <xsl:with-param name='hseq' select='substring($hseq, 1, 80)'/>
                        <xsl:with-param name='mseq' select='substring($mseq, 1, 80)'/>
                    </xsl:call-template>

                    <xsl:call-template name='align'>
                        <xsl:with-param name='qseq' select='substring($qseq, 51, string-length($qseq) - 80)'/>
                        <xsl:with-param name='hseq' select='substring($hseq, 51, string-length($hseq) - 80)'/>
                        <xsl:with-param name='mseq' select='substring($mseq, 51, string-length($mseq) - 80)'/>
                    </xsl:call-template>

                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name='align80'>
                        <xsl:with-param name='qseq' select='$qseq'/>
                        <xsl:with-param name='hseq' select='$hseq'/>
                        <xsl:with-param name='mseq' select='$mseq'/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>

    </xsl:template>

    <xsl:template name='align80'>
        <xsl:param name='qseq'/>
        <xsl:param name='hseq'/>
        <xsl:param name='mseq'/>
  
       
        <xsl:call-template name='highligth'>
            <xsl:with-param name='qseq' select='$qseq'/>
            <xsl:with-param name='hseq' select='$hseq'/>
        </xsl:call-template>
        <br/>

       
        <xsl:call-template name="whitespace">
            <xsl:with-param name="text" select="$mseq"/>
        </xsl:call-template>
        <br/>

       
        <xsl:call-template name='highligth'>
            <xsl:with-param name='qseq' select='$hseq'/>
            <xsl:with-param name='hseq' select='$qseq'/>
        </xsl:call-template>
        <br/>

    </xsl:template>

    <xsl:template name='highligth'>
        <xsl:param name='qseq'/>
        <xsl:param name='hseq'/>

        <xsl:if test='string-length($qseq) != 0' >
            <xsl:variable name="q_char" select="substring($qseq, 1, 1)" />
            <xsl:variable name="h_char" select="substring($hseq, 1, 1)" />

            <xsl:choose>
                <xsl:when test='$q_char = $h_char'>
                    <xsl:value-of select="$q_char"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name='colorer'>
                        <xsl:with-param name='char' select='$q_char'/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>

            <xsl:call-template name='highligth'>
                <xsl:with-param name='qseq' select='substring($qseq, 2, string-length($qseq) - 1)'/>
                <xsl:with-param name='hseq' select='substring($hseq, 2, string-length($hseq) - 1)'/>
            </xsl:call-template>

        </xsl:if>

    </xsl:template>
  
    <xsl:template name="colorer">
        <xsl:param name="char"/>

        <xsl:choose>
            <xsl:when test="$char='A'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='C'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='L'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='I'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='V'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='M'">
                <font style='background-color: #C8FFC8;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='G'">
                <font style='background-color: #DCC0FF;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='P'">
                <font style='background-color: #DCC0FF;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='S'">
                <font style='background-color: #FFFCA0;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='T'">
                <font style='background-color: #FFFCA0;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='N'">
                <font style='background-color: #FFFCA0;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='Q'">
                <font style='background-color: #FFFCA0;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='K'">
                <font style='background-color: #FFA2A2;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='R'">
                <font style='background-color: #FFA2A2;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='D'">
                <font style='background-color: #A2E2FF;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='E'">
                <font style='background-color: #A2E2FF;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='H'">
                <font style='background-color: #50FF50;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='Y'">
                <font style='background-color: #50FF50;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='W'">
                <font style='background-color: #50FF50;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:when test="$char='F'">
                <font style='background-color: #50FF50;'>
                    <xsl:value-of select="$char"/>
                </font>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$char"/>
            </xsl:otherwise>

        </xsl:choose>
    </xsl:template>


    <xsl:template name="whitespace">
        <xsl:param name="text"/>

        <xsl:choose>
            <xsl:when test="contains($text, ' ')">

                <xsl:variable name="before" select="substring-before($text, ' ')"/>
                <xsl:variable name="after" select="substring-after($text, ' ')"/>

                <xsl:value-of select="$before"/>
                <xsl:value-of select="' '"/>

                <xsl:call-template name="whitespace">
                    <xsl:with-param name="text" select="$after"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$text"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
