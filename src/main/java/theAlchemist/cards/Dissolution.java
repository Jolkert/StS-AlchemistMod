package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Dissolution extends CustomCard
{
	public static final String ID = AlchemistMod.makeID(SkillTemplate.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Dissolution.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int DAMAGE = 7;
	private static final int UPGRADE_PLUS_DAMAGE = 2;
	private static final int ENEMY_HAS_BLOCK_MULTIPLIER = 2;
	private static final int UPGRADE_PLUS_MULTIPLIER =1;
	
	
	public Dissolution()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
		this.baseDamage = DAMAGE;
		this.baseMagicNumber = ENEMY_HAS_BLOCK_MULTIPLIER;
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		int multiplier = monster.currentBlock > 0 ? this.baseMagicNumber : 1;
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage * multiplier, damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DAMAGE);
			upgradeMagicNumber(UPGRADE_PLUS_MULTIPLIER);
			initializeDescription();
		}
	}
}
